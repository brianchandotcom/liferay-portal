/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.status;

import java.util.Objects;

/**
 * @author Gavin Wan
 */
public enum ReportStatus {

	COMPLETE("complete"), ERROR("error"), PENDING("pending");

	public static ReportStatus parse(String value) {
		if (Objects.equals(PENDING.getValue(), value)) {
			return PENDING;
		}
		else if (Objects.equals(COMPLETE.getValue(), value)) {
			return COMPLETE;
		}
		else if (Objects.equals(ERROR.getValue(), value)) {
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