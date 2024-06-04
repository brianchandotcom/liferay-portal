/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.term.provider;

import java.util.List;

/**
 * @author Luca Pellizzon
 */
public interface NotificationTermProviderRegistry {

	public List<NotificationTermProvider> getNotificationTermProviders(
		String className);

}