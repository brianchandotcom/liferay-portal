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

package com.liferay.portal.reports.engine.console.status;

/**
 * @author Gavin Wan
 */
public enum ReportStatus {

	COMPLETE("complete"), ERROR("error"), PENDING("pending");

	public static ReportStatus parse(String value) {
		if (PENDING.getValue().equals(value)) {
			return PENDING;
		}
		else if (COMPLETE.getValue().equals(value)) {
			return COMPLETE;
		}
		else if (ERROR.getValue().equals(value)) {
			return ERROR;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ReportStatus(String value) {
		_value = value;
	}

	private final String _value;

}