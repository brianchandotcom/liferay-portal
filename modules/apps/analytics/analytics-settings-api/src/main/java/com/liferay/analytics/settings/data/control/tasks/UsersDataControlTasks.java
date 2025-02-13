/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.data.control.tasks;

import java.util.Set;

/**
 * @author Marcos Martins
 */
public interface UsersDataControlTasks {

	public void addEmailAddress(long companyId, String emailAddress);

	public void clean(long companyId);

	public Set<String> getEmailAddresses(long companyId);

}