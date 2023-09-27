/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface BackgroundTaskFinder {

	public java.util.List
		<com.liferay.portal.background.task.model.BackgroundTask> findByG_T_C(
			long[] groupIds, String[] taskExecutorClassNames, Boolean completed,
			int start, int end, boolean orderByType);

}