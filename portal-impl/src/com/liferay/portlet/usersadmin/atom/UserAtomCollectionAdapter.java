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

package com.liferay.portlet.usersadmin.atom;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseAtomCollectionAdapter;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public class UserAtomCollectionAdapter extends BaseAtomCollectionAdapter<User> {

	@Override
	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	@Override
	public List<String> getEntryAuthors(User user) {
		return ListUtil.fromArray(user.getFullName());
	}

	@Override
	public AtomEntryContent getEntryContent(
		User user, AtomRequestContext atomRequestContext) {

		StringBundler sb = new StringBundler();

		sb.append(user.getScreenName());
		sb.append(StringPool.NEW_LINE);
		sb.append(user.getEmailAddress());
		sb.append(StringPool.NEW_LINE);
		sb.append(user.getFullName());
		sb.append(StringPool.NEW_LINE);
		sb.append(user.getJobTitle());
		sb.append(StringPool.NEW_LINE);

		try {
			List<Address> userAddresses = user.getAddresses();

			for (Address address : userAddresses) {
				sb.append(address.getStreet1());
				sb.append(StringPool.NEW_LINE);
				sb.append(address.getStreet2());
				sb.append(StringPool.NEW_LINE);
				sb.append(address.getStreet3());
				sb.append(StringPool.NEW_LINE);
			}
		}
		catch (Exception e) {
		}

		return new AtomEntryContent(sb.toString());
	}

	@Override
	public String getEntryId(User user) {
		return String.valueOf(user.getUserId());
	}

	@Override
	public String getEntrySummary(User user) {
		return user.getFullName();
	}

	@Override
	public String getEntryTitle(User user) {
		return user.getScreenName();
	}

	@Override
	public Date getEntryUpdated(User user) {
		return user.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		String portletId = PortletProviderUtil.getPortletId(
			User.class.getName(), PortletProvider.Action.VIEW);

		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, portletId);
	}

	@Override
	protected User doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long userId = GetterUtil.getLong(resourceName);

		return UserServiceUtil.getUserById(userId);
	}

	@Override
	protected Iterable<User> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");

		if (groupId > 0) {
			return UserServiceUtil.getGroupUsers(groupId);
		}

		long organizationId = atomRequestContext.getLongParameter(
			"organizationId");

		if (organizationId > 0) {
			return UserServiceUtil.getOrganizationUsers(organizationId);
		}

		long userGroupId = atomRequestContext.getLongParameter("userGroupId");

		if (userGroupId > 0) {
			return UserServiceUtil.getUserGroupUsers(userGroupId);
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId > 0) {
			int usersCount = UserServiceUtil.getCompanyUsersCount(companyId);

			AtomPager atomPager = new AtomPager(atomRequestContext, usersCount);

			AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

			return UserServiceUtil.getCompanyUsers(
				companyId, atomPager.getStart(), atomPager.getEnd() + 1);
		}

		return Collections.emptyList();
	}

	private static final String _COLLECTION_NAME = "users";

}