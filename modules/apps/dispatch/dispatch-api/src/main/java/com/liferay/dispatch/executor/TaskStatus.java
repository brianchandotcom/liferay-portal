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

package com.liferay.dispatch.executor;

import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;

/**
 * @author Igor Beslic
 */
public enum TaskStatus {

	CANCELED(
		BackgroundTaskConstants.STATUS_CANCELLED,
		BackgroundTaskConstants.LABEL_CANCELLED),
	FAILED(
		BackgroundTaskConstants.STATUS_FAILED,
		BackgroundTaskConstants.LABEL_FAILED),
	IN_PROGRESS(
		BackgroundTaskConstants.STATUS_IN_PROGRESS,
		BackgroundTaskConstants.LABEL_IN_PROGRESS),
	SUCCESSFUL(
		BackgroundTaskConstants.STATUS_SUCCESSFUL,
		BackgroundTaskConstants.LABEL_SUCCESSFUL);

	public static TaskStatus valueOf(int status) {
		for (TaskStatus taskStatus : values()) {
			if (status == taskStatus._status) {
				return taskStatus;
			}
		}

		throw new IllegalArgumentException(
			"Illegal task status value " + status);
	}

	public String getLabel() {
		return _label;
	}

	public int getStatus() {
		return _status;
	}

	private TaskStatus(int status, String label) {
		_status = status;
		_label = label;
	}

	private final String _label;
	private final int _status;

}