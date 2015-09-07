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

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public interface SchedulerEntry extends Serializable {

	public String getDescription();

	public String getEventListenerClass();

	public TimeUnit getTimeUnit();

	public Trigger getTrigger() throws SchedulerException;

	public TriggerType getTriggerType();

	public String getTriggerValue();

	public void setDescription(String description);

	public void setEventListenerClass(String eventListenerClass);

	public void setTimeUnit(TimeUnit timeUnit);

	public void setTriggerType(TriggerType triggerType);

	public void setTriggerValue(int triggerValue);

	public void setTriggerValue(String triggerValue);

}