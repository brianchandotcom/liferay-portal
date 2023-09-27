/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.manager;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pei-Jung Lan
 */
public interface CurrentAccountEntryManager {

	public AccountEntry getCurrentAccountEntry(long groupId, long userId)
		throws PortalException;

	public void setCurrentAccountEntry(
			long accountEntryId, long groupId, long userId)
		throws PortalException;

}