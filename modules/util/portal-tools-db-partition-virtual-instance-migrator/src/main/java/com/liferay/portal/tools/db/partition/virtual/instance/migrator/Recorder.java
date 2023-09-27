/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class Recorder {

	public boolean hasErrors() {
		return !_errors.isEmpty();
	}

	public boolean hasWarnings() {
		return !_warnings.isEmpty();
	}

	public void printErrors() {
		for (String error : _errors) {
			System.out.println("[ERROR] " + error);
		}
	}

	public void printMessages() {
		printErrors();
		printWarnings();
	}

	public void printWarnings() {
		for (String warning : _warnings) {
			System.out.println("[WARN] " + warning);
		}
	}

	public void registerError(String message) {
		_errors.add(message);
	}

	public void registerErrors(String message, List<String> moduleNames) {
		for (String moduleName : moduleNames) {
			_errors.add(
				StringBundler.concat("Module ", moduleName, " ", message));
		}
	}

	public void registerWarning(String message) {
		_warnings.add(message);
	}

	public void registerWarnings(String message, List<String> moduleNames) {
		for (String moduleName : moduleNames) {
			_warnings.add(
				StringBundler.concat("Module ", moduleName, " ", message));
		}
	}

	private final List<String> _errors = new ArrayList<>();
	private final List<String> _warnings = new ArrayList<>();

}