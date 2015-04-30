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

package com.liferay.sass.compiler.jni.internal.util.test;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;

import java.io.File;
import java.io.IOException;

/**
 * @author Gregory Amerson
 */
public class JniSassCompilerTestUtil {

	public static void addSearchPath() throws IOException {
		NativeLibrary.addSearchPath("liferaysass", _getResourcesPath());
	}

	private static String _getResourcesPath() throws IOException {
		StringBuilder sb = new StringBuilder("resources/");

		if (Platform.isLinux()) {
			sb.append("linux");
		}
		else if (Platform.isMac()) {
			sb.append("darwin");
		}
		else if (Platform.isWindows()) {
			sb.append("win32");
		}

		if (!Platform.isMac()) {
			sb.append("-x86");

			if (Platform.is64Bit()) {
				sb.append("-64");
			}
		}

		File file = new File(sb.toString());

		return file.getCanonicalPath();
	}

}