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

package com.liferay.gradle.plugins.util;

import groovy.lang.Closure;

import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
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