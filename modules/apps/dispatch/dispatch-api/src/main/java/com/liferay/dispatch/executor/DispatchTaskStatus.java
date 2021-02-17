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