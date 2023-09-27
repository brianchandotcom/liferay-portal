/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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