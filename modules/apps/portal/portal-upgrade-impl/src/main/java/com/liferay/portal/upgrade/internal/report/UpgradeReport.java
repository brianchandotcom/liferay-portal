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

package com.liferay.portal.upgrade.internal.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public void addError(String loggerName, String message) {
		List<String> errors = _errors.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		errors.add(message);
	}

	public void addEvent(String loggerName, String message) {
		List<String> events = _events.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		events.add(message);
	}

	public void addWarning(String loggerName, String message) {
		List<String> warnings = _warnings.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		warnings.add(message);
	}

	public void generateReport() {
	}

	private final Map<String, ArrayList<String>> _errors = new HashMap<>();
	private final Map<String, ArrayList<String>> _events = new HashMap<>();
	private final Map<String, ArrayList<String>> _warnings = new HashMap<>();

}