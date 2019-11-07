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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Map;

/**
 * @author Samuel Trong Tran
 */
public abstract class BaseCTProcessMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected int getStatus(String type) {
		return _statuses.getOrDefault(type, 0);
	}

	private static final Map<String, Integer> _statuses =
		HashMapBuilder.<String, Integer>put(
			"all", WorkflowConstants.STATUS_ANY
		).put(
			"failed", BackgroundTaskConstants.STATUS_FAILED
		).put(
			"in-progress", BackgroundTaskConstants.STATUS_IN_PROGRESS
		).put(
			"published", BackgroundTaskConstants.STATUS_SUCCESSFUL
		).build();

}