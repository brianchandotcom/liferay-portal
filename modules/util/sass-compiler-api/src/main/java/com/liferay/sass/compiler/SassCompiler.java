/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sass.compiler;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author David Truong
 */
public interface SassCompiler extends Closeable {

	@Override
	public default void close() throws IOException {
	}

	public String compileFile(String inputFileName, String includeDirName)
		throws SassCompilerException;

	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws SassCompilerException;

	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws SassCompilerException;

	public String compileString(String input, String includeDirName)
		throws SassCompilerException;

	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws SassCompilerException;

	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws SassCompilerException;

}