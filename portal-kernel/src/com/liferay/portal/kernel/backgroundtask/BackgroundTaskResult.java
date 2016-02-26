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

package com.liferay.portal.kernel.backgroundtask;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskResult implements Serializable {

	public static BackgroundTaskResult SUCCESS = new BackgroundTaskResult(
		BackgroundTaskConstants.STATUS_SUCCESSFUL);

	public BackgroundTaskResult() {
	}

	public BackgroundTaskResult(int status) {
		_status = status;
	}

	public BackgroundTaskResult(int status, String statusMessage) {
		_status = status;
		_statusMessage = statusMessage;
	}

	public int getStatus() {
		return _status;
	}

	public String getStatusMessage() {
		return _statusMessage;
	}

	public boolean isSuccessful() {
		if (_status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			return true;
		}

		return false;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;
	}

	private int _status;
	private String _statusMessage;

}