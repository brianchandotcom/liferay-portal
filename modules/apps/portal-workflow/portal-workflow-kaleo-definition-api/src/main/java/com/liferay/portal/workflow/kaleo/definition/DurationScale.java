/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Objects;

/**
 * @author Michael C. Han
 * @author Péter Borkuti
 */
public enum DurationScale {

	DAY("day"), HOUR("hour"), MILLISECOND("millisecond"), MINUTE("minute"),
	MONTH("month"), SECOND("second"), WEEK("week"), YEAR("year");

	public static DurationScale parse(String value)
		throws KaleoDefinitionValidationException {

		if (Objects.equals(DAY.getValue(), value)) {
			return DAY;
		}
		else if (Objects.equals(HOUR.getValue(), value)) {
			return HOUR;
		}
		else if (Objects.equals(MILLISECOND.getValue(), value)) {
			return MILLISECOND;
		}
		else if (Objects.equals(MINUTE.getValue(), value)) {
			return MINUTE;
		}
		else if (Objects.equals(MONTH.getValue(), value)) {
			return MONTH;
		}
		else if (Objects.equals(SECOND.getValue(), value)) {
			return SECOND;
		}
		else if (Objects.equals(WEEK.getValue(), value)) {
			return WEEK;
		}
		else if (Objects.equals(YEAR.getValue(), value)) {
			return YEAR;
		}

		throw new KaleoDefinitionValidationException.InvalidDurationScale(
			value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private DurationScale(String value) {
		_value = value;
	}

	private final String _value;

}