/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.sync;

/**
 * @author Carlos Correa
 */
public interface LayoutSetPrototypeSyncSessionManager {

	public void openSession(
		int expectedCount, String siteTemplateName, String syncSessionId,
		long userId);

	public void recordBackgroundTaskStatus(
		int backgroundTaskStatus, String syncSessionId);

}