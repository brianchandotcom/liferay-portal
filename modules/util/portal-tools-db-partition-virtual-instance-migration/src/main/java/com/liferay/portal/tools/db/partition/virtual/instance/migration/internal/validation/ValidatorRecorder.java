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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class ValidatorRecorder {

	public boolean hasErrors() {
		return !_errors.isEmpty();
	}

	public boolean hasWarnings() {
		return !_warnings.isEmpty();
	}

	public void printErrors() {
		for (String error : _errors) {
			System.out.println("ERROR: " + error);
		}
	}

	public void printMessages() {
		printErrors();
		printWarnings();
	}

	public void printWarnings() {
		for (String warning : _warnings) {
			System.out.println("WARNING: " + warning);
		}
	}

	public void registerError(String message) {
		_errors.add(message);
	}

	public void registerErrors(
		List<String> servletContextNames, String message) {

		for (String module : servletContextNames) {
			_errors.add("Module " + module + " " + message);
		}
	}

	public void registerWarning(String message) {
		_warnings.add(message);
	}

	public void registerWarnings(
		List<String> servletContextNames, String message) {

		for (String module : servletContextNames) {
			_warnings.add("Module " + module + " " + message);
		}
	}

	private final ArrayList<String> _errors = new ArrayList<>();
	private final ArrayList<String> _warnings = new ArrayList<>();

}