/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;

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