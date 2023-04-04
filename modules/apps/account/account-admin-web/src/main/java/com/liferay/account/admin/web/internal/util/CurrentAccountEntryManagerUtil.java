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

package com.liferay.account.admin.web.internal.util;

import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pei-Jung Lan
 */
public class CurrentAccountEntryManagerUtil {

	public static long getCurrentAccountEntryId(long groupId, long userId)
		throws PortalException {

		CurrentAccountEntryManager currentAccountEntryManager =
			_currentAccountEntryManagerSnapshot.get();

		AccountEntry accountEntry =
			currentAccountEntryManager.getCurrentAccountEntry(groupId, userId);

		if (accountEntry != null) {
			return accountEntry.getAccountEntryId();
		}

		return 0;
	}

	public void setCurrentAccountEntry(
			long accountEntryId, long groupId, long userId)
		throws PortalException {

		CurrentAccountEntryManager currentAccountEntryManager =
			_currentAccountEntryManagerSnapshot.get();

		currentAccountEntryManager.setCurrentAccountEntry(
			accountEntryId, groupId, userId);
	}

	private static final Snapshot<CurrentAccountEntryManager>
		_currentAccountEntryManagerSnapshot = new Snapshot<>(
			CurrentAccountEntryManagerUtil.class,
			CurrentAccountEntryManager.class);

}