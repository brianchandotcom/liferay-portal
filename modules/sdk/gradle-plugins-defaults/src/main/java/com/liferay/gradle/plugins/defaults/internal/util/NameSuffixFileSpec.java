/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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