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

package com.liferay.dispatch.rest.internal.dto.v1_0.util;

import com.liferay.dispatch.rest.dto.v1_0.DispatchTrigger;

/**
 * @author Nilton Vieira
 */
public class DispatchTriggerUtil {

	public static DispatchTrigger toDispatchTrigger(
		com.liferay.dispatch.model.DispatchTrigger dispatchTrigger) {

		return new DispatchTrigger() {
			{
				active = dispatchTrigger.getActive();
				companyId = dispatchTrigger.getCompanyId();
				cronExpression = dispatchTrigger.getCronExpression();
				dispatchTaskClusterMode =
					dispatchTrigger.getDispatchTaskClusterMode();
				dispatchTaskExecutorType =
					dispatchTrigger.getDispatchTaskExecutorType();
				dispatchTaskSettings =
					dispatchTrigger.getDispatchTaskSettings();
				endDate = dispatchTrigger.getEndDate();
				id = dispatchTrigger.getDispatchTriggerId();
				name = dispatchTrigger.getName();
				overlapAllowed = dispatchTrigger.getOverlapAllowed();
				startDate = dispatchTrigger.getStartDate();
				system = dispatchTrigger.getSystem();
				timeZoneId = dispatchTrigger.getTimeZoneId();
				userId = dispatchTrigger.getUserId();
			}
		};
	}

}