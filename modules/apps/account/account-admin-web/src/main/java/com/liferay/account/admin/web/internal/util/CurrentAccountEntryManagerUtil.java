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
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class CurrentAccountEntryManagerUtil {

	public static long getCurrentAccountEntryId(long groupId, long userId)
		throws PortalException {

		AccountEntry accountEntry =
			_currentAccountEntryManager.getCurrentAccountEntry(groupId, userId);

		if (accountEntry != null) {
			return accountEntry.getAccountEntryId();
		}

		return 0;
	}

	public void setCurrentAccountEntry(
			long accountEntryId, long groupId, long userId)
		throws PortalException {

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntryId, groupId, userId);
	}

	@Reference(unbind = "-")
	protected void setCurrentAccountEntryManager(
		CurrentAccountEntryManager currentAccountEntryManager) {

		_currentAccountEntryManager = currentAccountEntryManager;
	}

	private static CurrentAccountEntryManager _currentAccountEntryManager;

}