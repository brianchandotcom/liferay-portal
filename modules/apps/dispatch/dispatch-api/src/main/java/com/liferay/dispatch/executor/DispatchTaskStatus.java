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

	CANCELED(3, "cancelled"), FAILED(4, "failed"),
	IN_PROGRESS(1, "in-progress"), SUCCESSFUL(2, "successful");

	public static DispatchTaskStatus valueOf(int status) {
		for (DispatchTaskStatus dispatchTaskStatus : values()) {
			if (status == dispatchTaskStatus._status) {
				return dispatchTaskStatus;
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

	private DispatchTaskStatus(int status, String label) {
		_status = status;
		_label = label;
	}

	private final String _label;
	private final int _status;

}