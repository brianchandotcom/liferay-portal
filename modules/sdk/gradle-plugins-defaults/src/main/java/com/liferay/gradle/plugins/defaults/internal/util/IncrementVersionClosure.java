/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

import groovy.lang.Closure;

import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
@SuppressWarnings("serial")
public class IncrementVersionClosure extends Closure<String> {

	public static final IncrementVersionClosure MICRO_INCREMENT =
		new IncrementVersionClosure(0, 0, 1);

	public IncrementVersionClosure(
		int majorIncrement, int minorIncrement, int microIncrement) {

		super(null);

		_majorIncrement = majorIncrement;
		_minorIncrement = minorIncrement;
		_microIncrement = microIncrement;
	}

	public String doCall(String version) {
		VersionNumber versionNumber = VersionNumber.parse(version);

		VersionNumber newVersionNumber = new VersionNumber(
			versionNumber.getMajor() + _majorIncrement,
			versionNumber.getMinor() + _minorIncrement,
			versionNumber.getMicro() + _microIncrement,
			versionNumber.getQualifier());

		return newVersionNumber.toString();
	}

	private final int _majorIncrement;
	private final int _microIncrement;
	private final int _minorIncrement;

}