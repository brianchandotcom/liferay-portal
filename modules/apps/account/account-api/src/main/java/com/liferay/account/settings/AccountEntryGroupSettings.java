/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.settings;

import com.liferay.account.exception.AccountEntryTypeException;

/**
 * @author Drew Brokke
 */
public interface AccountEntryGroupSettings {

	public String[] getAllowedTypes(long groupId);

	public void setAllowedTypes(long groupId, String[] types)
		throws AccountEntryTypeException;

}