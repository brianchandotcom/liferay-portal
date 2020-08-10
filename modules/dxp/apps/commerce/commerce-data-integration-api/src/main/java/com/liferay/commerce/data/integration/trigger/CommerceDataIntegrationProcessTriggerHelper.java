/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.trigger;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceDataIntegrationProcessTriggerHelper {

	public void addScheduledTask(
			long commerceDataIntegrationProcessId, String cronExpression,
			Date startDate, Date endDate)
		throws SchedulerException;

	public void deleteScheduledTask(long commerceDataIntegrationProcessId)
		throws SchedulerException;

	public Date getNextFireTime(long commerceDataIntegrationProcessId);

	public Date getPreviousFireTime(long commerceDataIntegrationProcessId);

	public SchedulerResponse getScheduledJob(
			long commerceDataIntegrationProcessId)
		throws SchedulerException;

}