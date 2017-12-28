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

package com.liferay.gradle.plugins.defaults.internal.util;

import java.io.File;

import org.gradle.api.specs.Spec;

/**
 * @author Andrea Di Giorgi
 */
public class NameSuffixFileSpec implements Spec<File> {

	public NameSuffixFileSpec(String... suffixes) {
		_suffixes = suffixes;
	}

	@Override
	public boolean isSatisfiedBy(File file) {
		String name = file.getName();

		for (String suffix : _suffixes) {
			if (name.endsWith(suffix)) {
				return true;
			}
		}

		return false;
	}

	private final String[] _suffixes;

}