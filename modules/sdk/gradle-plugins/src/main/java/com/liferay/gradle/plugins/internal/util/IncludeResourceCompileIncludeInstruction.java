/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal.util;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.GradleException;

/**
 * @author Andrea Di Giorgi
 */
public class IncludeResourceCompileIncludeInstruction {

	public IncludeResourceCompileIncludeInstruction(
		Callable<Iterable<File>> filesIterable,
		Callable<Boolean> expandCallable) {

		_filesIterable = filesIterable;
		_expandCallable = expandCallable;
	}

	@Override
	public String toString() {
		boolean expand = false;
		Iterable<File> files = null;

		try {
			expand = _expandCallable.call();
			files = _filesIterable.call();
		}
		catch (Exception exception) {
			throw new GradleException("Unable to build instruction", exception);
		}

		StringBuilder sb = new StringBuilder();

		for (File file : files) {
			if (sb.length() > 0) {
				sb.append(',');
			}

			if (expand) {
				sb.append('@');
			}
			else {
				sb.append("lib/=");
			}

			String absolutePath = file.getAbsolutePath();

			if (File.separatorChar != '/') {
				absolutePath = absolutePath.replace(File.separatorChar, '/');
			}

			sb.append(absolutePath);

			if (!expand) {
				sb.append(";lib:=true");
			}
			else {
				sb.append("!/!META-INF/versions/*");
			}
		}

		return sb.toString();
	}

	private final Callable<Boolean> _expandCallable;
	private final Callable<Iterable<File>> _filesIterable;

}