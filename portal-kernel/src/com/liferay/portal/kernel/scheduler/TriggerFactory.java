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

package com.liferay.portal.kernel.scheduler;

import java.util.Date;
import java.util.TimeZone;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Tina Tian
 */
@ProviderType
public interface TriggerFactory {

	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		int interval, TimeUnit timeUnit);

	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression);

	public Trigger createTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		String cronExpression, TimeZone timeZone);

	public Trigger createTrigger(Trigger trigger, Date startDate, Date endDate);

}