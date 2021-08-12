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
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountEntryUserRelImpl extends AccountEntryUserRelBaseImpl {

	@Override
	public AccountEntry fetchAccountEntry() {
		return AccountEntryLocalServiceUtil.fetchAccountEntry(
			getAccountEntryId());
	}

	@Override
	public User fetchUser() {
		return UserLocalServiceUtil.fetchUser(getAccountUserId());
	}

	@Override
	public AccountEntry getAccountEntry() throws PortalException {
		return AccountEntryLocalServiceUtil.getAccountEntry(
			getAccountEntryId());
	}

	@Override
	public User getUser() throws PortalException {
		return UserLocalServiceUtil.getUser(getAccountUserId());
	}

}