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

package com.liferay.tools.jsc;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.io.File;

import sass.OutputStyle;
import sass.SassLibrary;
import sass.SourceComments;
import sass.sass_context;
import sass.sass_file_context;

/**
 * @author Gregory Amerson
 */
public class SassCompiler {

	public String compile(String input, String includePath, String imgPath)
		throws SassCompilationException {

		sass_context sassContext = null;

		try {
			sassContext = _libsass.sass_new_context();
			sassContext.options.image_path = str(imgPath);
			sassContext.options.include_paths = str(includePath);
			sassContext.options.output_style = OutputStyle.EXPANDED.value();
			sassContext.options.source_comments = SourceComments.NONE.value();
			sassContext.source_string = str(input);

			_libsass.sass_compile(sassContext);

			if (sassContext.error_status != 0) {
				throw new SassCompilationException(
					sassContext.error_message.getString(0));
			}

			if (sassContext.output_string == null ||
				sassContext.output_string.getString(0) == null) {

				throw new SassCompilationException("libsass returned null");
			}

			return sassContext.output_string.getString(0);
		} finally {
			try {
				if (sassContext != null) {
					_libsass.sass_free_context(sassContext);
				}
			} catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	public String compileFile(
			String inputFile, String includePath, String imgPath)
		throws SassCompilationException {

		sass_file_context sassFileContext = null;

		try {
			sassFileContext = _libsass.sass_new_file_context();

			sassFileContext.input_path = str(inputFile);
			sassFileContext.output_path = str("");

			String includePaths = includePath +
					File.pathSeparator + new File(inputFile).getParent();

			sassFileContext.options.image_path = str(imgPath);
			sassFileContext.options.include_paths = str(includePaths);
			sassFileContext.options.output_style = OutputStyle.EXPANDED.value();
			sassFileContext.options.source_comments = SourceComments.NONE.value();
			sassFileContext.source_map_string = str("");

			_libsass.sass_compile_file(sassFileContext);

			if (sassFileContext.error_status != 0) {
				throw new SassCompilationException(
					sassFileContext.error_message.getString(0));
			}

			if (sassFileContext.output_string == null ||
				sassFileContext.output_string.getString(0) == null) {

				throw new SassCompilationException("libsass returned null");
			}

			return sassFileContext.output_string.getString(0);
		} finally {
			try {
				if (sassFileContext != null) {
					_libsass.sass_free_file_context(sassFileContext);
				}
			} catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	private Pointer str(String src) {
		Memory mem = new Memory(src.length() + 10);
		mem.setString(0, src);
		return mem;
	}

	private static SassLibrary _libsass = SassLibrary.INSTANCE;
}