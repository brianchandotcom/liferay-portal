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

package com.liferay.portal.tools.db.virtual.instance.migration.internal.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class ValidatorRecorder {

	public static void printMessages() {
		for (String error : _errors) {
			System.out.println("ERROR: " + error);
		}

		for (String warning : _warnings) {
			System.out.println("WARNING: " + warning);
		}
	}

	public static void registerError(String message) {
		_errors.add(message);
	}

	public static void registerErrors(List<String> modules, String message) {
		for (String module : modules) {
			_errors.add("Module " + module + message);
		}
	}

	public static void registerWarning(String message) {
		_warnings.add(message);
	}

	public static void registerWarnings(List<String> modules, String message) {
		for (String module : modules) {
			_warnings.add("Module " + module + message);
		}
	}

	private static final ArrayList<String> _errors = new ArrayList<>();
	private static final ArrayList<String> _warnings = new ArrayList<>();

}