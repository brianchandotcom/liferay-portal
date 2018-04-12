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
		catch (Exception e) {
			throw new GradleException("Unable to build instruction", e);
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

			sb.append(file.getAbsolutePath());

			if (!expand) {
				sb.append(";lib:=true");
			}
		}

		return sb.toString();
	}

	private final Callable<Boolean> _expandCallable;
	private final Callable<Iterable<File>> _filesIterable;

}