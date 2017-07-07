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