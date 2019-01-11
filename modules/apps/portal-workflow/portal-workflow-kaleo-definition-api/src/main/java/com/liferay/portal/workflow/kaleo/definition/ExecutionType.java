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

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum ExecutionType {

	ON_ASSIGNMENT("onAssignment"), ON_ENTRY("onEntry"), ON_EXIT("onExit"),
	ON_TIMER("onTimer");

	public static ExecutionType parse(String value) {
		if (Objects.equals(ON_ASSIGNMENT.getValue(), value)) {
			return ON_ASSIGNMENT;
		}
		else if (Objects.equals(ON_ENTRY.getValue(), value)) {
			return ON_ENTRY;
		}
		else if (Objects.equals(ON_EXIT.getValue(), value)) {
			return ON_EXIT;
		}
		else if (Objects.equals(ON_TIMER.getValue(), value)) {
			return ON_TIMER;
		}
		else {
			throw new IllegalArgumentException("Invalid value " + value);
		}
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ExecutionType(String value) {
		_value = value;
	}

	private final String _value;

}