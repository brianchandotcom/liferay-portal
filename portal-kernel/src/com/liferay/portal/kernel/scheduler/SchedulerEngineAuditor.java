/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.Message;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Dante Wang
 */
@ProviderType
public interface SchedulerEngineAuditor {

	public void auditSchedulerJobs(Message message, TriggerState triggerState)
		throws SchedulerException;

}