/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.executor;

/**
 * @author Igor Beslic
 */
public enum DispatchTaskStatus {

	CANCELED("text-info", "cancelled", 3), FAILED("text-danger", "failed", 4),
	IN_PROGRESS("text-warning", "in-progress", 1),
	NEVER_RAN("text-info", "never-ran", 5),
	SUCCESSFUL("text-success", "successful", 2);

	public static DispatchTaskStatus valueOf(int status) {
		for (DispatchTaskStatus dispatchTaskStatus : values()) {
			if (status == dispatchTaskStatus._status) {
				return dispatchTaskStatus;
			}
		}

		throw new IllegalArgumentException(
			"Illegal task status value " + status);
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getLabel() {
		return _label;
	}

	public int getStatus() {
		return _status;
	}

	private DispatchTaskStatus(String cssClass, String label, int status) {
		_cssClass = cssClass;
		_label = label;
		_status = status;
	}

	private final String _cssClass;
	private final String _label;
	private final int _status;

}