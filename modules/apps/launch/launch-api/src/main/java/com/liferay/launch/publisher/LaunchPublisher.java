/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.publisher;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface LaunchPublisher {

	public void publish(long launchId, long userId) throws PortalException;

	public void schedulePublish(Date date, long launchId, long userId)
		throws PortalException;

	public void unschedulePublish(long launchId) throws PortalException;

}