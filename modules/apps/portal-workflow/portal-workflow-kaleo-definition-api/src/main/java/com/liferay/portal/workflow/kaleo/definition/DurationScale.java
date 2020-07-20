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
		else {
			throw new KaleoDefinitionValidationException.InvalidDurationScale(
				value);
		}
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